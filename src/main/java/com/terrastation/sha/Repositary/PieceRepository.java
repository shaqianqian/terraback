package com.terrastation.sha.Repositary;


import com.terrastation.sha.Entity.Piece;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PieceRepository extends JpaRepository<Piece,Integer> {

}