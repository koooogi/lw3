package com.example.lab3.Repository;

import com.example.lab3.Model.Curse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurseRepository extends JpaRepository<Curse, Long>{}
