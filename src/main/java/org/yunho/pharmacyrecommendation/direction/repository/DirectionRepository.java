package org.yunho.pharmacyrecommendation.direction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yunho.pharmacyrecommendation.direction.entity.Direction;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
}
