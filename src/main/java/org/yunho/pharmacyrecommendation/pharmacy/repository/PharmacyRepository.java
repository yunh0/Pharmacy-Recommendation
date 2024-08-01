package org.yunho.pharmacyrecommendation.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yunho.pharmacyrecommendation.pharmacy.entity.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
}
