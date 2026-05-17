package com.example.lab3.Repository;

import com.example.lab3.Model.Sorcerer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SorcererRepository extends JpaRepository<Sorcerer, Long>{}
