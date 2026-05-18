// API URL
const API_URL = 'http://localhost:8080/api/missions';

// DOM Elements
const fileInput = document.getElementById('fileInput');
const selectFileBtn = document.getElementById('selectFileBtn');
const uploadArea = document.getElementById('uploadArea');
const uploadResult = document.getElementById('uploadResult');
const missionsList = document.getElementById('missionsList');
const refreshBtn = document.getElementById('refreshBtn');
const applyFiltersBtn = document.getElementById('applyFilters');
const resetFiltersBtn = document.getElementById('resetFilters');
const filterMissionId = document.getElementById('filterMissionId');
const filterOutcome = document.getElementById('filterOutcome');

// Modal Elements
const reportModal = document.getElementById('reportModal');
const modalClose = document.querySelector('.modal-close');
const reportType = document.getElementById('reportType');
const generateReportBtn = document.getElementById('generateReportBtn');
const reportContent = document.getElementById('reportContent');

let currentMissions = [];

// ============= Initialize =============
document.addEventListener('DOMContentLoaded', () => {
    loadMissions();
    
    // Upload handlers
    selectFileBtn.addEventListener('click', () => fileInput.click());
    uploadArea.addEventListener('click', () => fileInput.click());
    uploadArea.addEventListener('dragover', (e) => {
        e.preventDefault();
        uploadArea.style.borderColor = '#c77dff';
    });
    uploadArea.addEventListener('dragleave', () => {
        uploadArea.style.borderColor = 'rgba(199, 125, 255, 0.4)';
    });
    uploadArea.addEventListener('drop', (e) => {
        e.preventDefault();
        uploadArea.style.borderColor = 'rgba(199, 125, 255, 0.4)';
        const file = e.dataTransfer.files[0];
        if (file) uploadMission(file);
    });
    fileInput.addEventListener('change', (e) => {
        if (e.target.files[0]) uploadMission(e.target.files[0]);
    });
    
    // Refresh
    refreshBtn.addEventListener('click', () => loadMissions());
    
    // Filters
    applyFiltersBtn.addEventListener('click', applyFilters);
    resetFiltersBtn.addEventListener('click', resetFilters);
    
    // Modal
    modalClose.addEventListener('click', () => {
        reportModal.classList.add('hidden');
    });
    generateReportBtn.addEventListener('click', generateReport);
    
    // Close modal on outside click
    reportModal.addEventListener('click', (e) => {
        if (e.target === reportModal) {
            reportModal.classList.add('hidden');
        }
    });
});

// ============= Load Missions =============
async function loadMissions() {
    showLoading();
    try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error('Failed to load missions');
        currentMissions = await response.json();
        renderMissions(currentMissions);
    } catch (error) {
        console.error('Error loading missions:', error);
        missionsList.innerHTML = '<div class="loading">❌ Error loading missions</div>';
    }
}

// ============= Render Missions =============
function renderMissions(missions) {
    if (!missions || missions.length === 0) {
        missionsList.innerHTML = '<div class="loading">📭 No missions loaded</div>';
        return;
    }
    
    missionsList.innerHTML = missions.map(mission => `
        <div class="mission-item" data-mission-id="${mission.missionId}" data-mission-id-db="${mission.id}">
            <div class="mission-info">
                <div class="mission-id">${escapeHtml(mission.missionId)}</div>
                <div class="mission-meta">
                    <span>📅 ${escapeHtml(mission.date) || '—'}</span>
                    <span>📍 ${escapeHtml(mission.location) || '—'}</span>
                    <span>💰 ¥${mission.damageCost?.toLocaleString() || 0}</span>
                    <span class="mission-outcome outcome-${mission.outcome}">${escapeHtml(mission.outcome) || '—'}</span>
                </div>
            </div>
            <div class="mission-actions">
                <button class="btn-view" onclick="openReportModal('${mission.id}', '${mission.missionId}')">📄</button>
                <button class="btn-delete" onclick="deleteMission('${mission.id}')">🗑️</button>
            </div>
        </div>
    `).join('');
}

// ============= Upload Mission =============
async function uploadMission(file) {
    const formData = new FormData();
    formData.append('file', file);
    
    showUploadResult('Uploading...', 'info');
    
    try {
        const response = await fetch(`${API_URL}/upload`, {
            method: 'POST',
            body: formData
        });
        
        if (!response.ok) {
            const error = await response.text();
            throw new Error(error || 'Upload failed');
        }
        
        const mission = await response.json();
        showUploadResult(`✅ Mission ${mission.missionId} uploaded successfully!`, 'success');
        loadMissions();
        
        setTimeout(() => {
            uploadResult.classList.add('hidden');
        }, 3000);
        
    } catch (error) {
        console.error('Error uploading mission:', error);
        showUploadResult(`❌ Error: ${error.message}`, 'error');
    }
}

// ============= Delete Mission =============
async function deleteMission(id) {
    if (!confirm('Delete this mission?')) return;
    
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            loadMissions();
        } else {
            alert('Error deleting mission');
        }
    } catch (error) {
        console.error('Error deleting mission:', error);
        alert('Error deleting mission');
    }
}

// ============= Open Report Modal =============
let currentReportId = null;

function openReportModal(id, missionId) {
    currentReportId = id;
    reportType.value = 'full';
    reportContent.textContent = 'Select report type and click "Generate"';
    reportModal.classList.remove('hidden');
}

// ============= Generate Report =============
async function generateReport() {
    if (!currentReportId) return;
    
    const type = reportType.value;
    reportContent.textContent = 'Generating report...';
    
    try {
        const response = await fetch(`${API_URL}/${currentReportId}/report?type=${type}`);
        
        if (!response.ok) throw new Error('Failed to generate report');
        
        const report = await response.text();
        reportContent.textContent = report;
        
    } catch (error) {
        console.error('Error generating report:', error);
        reportContent.textContent = `❌ Error generating report: ${error.message}`;
    }
}

// ============= Filters =============
async function applyFilters() {
    const missionId = filterMissionId.value.trim();
    const outcome = filterOutcome.value;
    
    if (missionId) {
        try {
            const response = await fetch(`${API_URL}/mission-id/${encodeURIComponent(missionId)}`);
            if (response.ok) {
                const mission = await response.json();
                renderMissions([mission]);
            } else {
                renderMissions([]);
            }
        } catch (error) {
            renderMissions([]);
        }
        return;
    }
    
    let filtered = [...currentMissions];
    
    if (outcome) {
        filtered = filtered.filter(m => m.outcome === outcome);
    }
    
    renderMissions(filtered);
}

function resetFilters() {
    filterMissionId.value = '';
    filterOutcome.value = '';
    renderMissions(currentMissions);
}

// ============= Helper Functions =============
function showLoading() {
    missionsList.innerHTML = '<div class="loading">🌀 Loading missions...</div>';
}

function showUploadResult(message, type) {
    uploadResult.textContent = message;
    uploadResult.className = `upload-result ${type}`;
    uploadResult.classList.remove('hidden');
}

function escapeHtml(text) {
    if (!text) return '';
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}