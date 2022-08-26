package me.longli.demo.jpa.repository;

import me.longli.demo.jpa.entity.Tb1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Tb1Repository extends JpaRepository<Tb1, Long> {
}
