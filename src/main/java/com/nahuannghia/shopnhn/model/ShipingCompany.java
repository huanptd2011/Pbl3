package com.nahuannghia.shopnhn.model;

import java.math.BigDecimal;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Table(name = "shiping_company")
public class ShipingCompany {
    @Id
    @Column(name = "shiping_company_id", length = 30, nullable = false)
    private String shipingCompanyId;
    @Column(name = "shiping_company_name", length = 255, nullable = false)
    private String shipingCompanyName;
    @Column(name = "shiping_cost", precision = 10,scale = 2, nullable = false)
    private BigDecimal shipingCost;
    @Column(name = "shiping_company_description", length = 2000)
    private String shipingCompanyDescription;

    public ShipingCompany() {
    }

    public ShipingCompany(String shipingCompanyId, String shipingCompanyName, BigDecimal shipingCost,
            String shipingCompanyDescription) {
        this.shipingCompanyId = shipingCompanyId;
        this.shipingCompanyName = shipingCompanyName;
        this.shipingCost = shipingCost;
        this.shipingCompanyDescription = shipingCompanyDescription;
    }

    // Getters
    public String getShipingCompanyId() {
        return shipingCompanyId;
    }

    public String getShipingCompanyName() {
        return shipingCompanyName;
    }

    public BigDecimal getShipingCost() {
        return shipingCost;
    }

    public String getShipingCompanyDescription() {
        return shipingCompanyDescription;
    }

    // Setters
    public void setShipingCompanyId(String shipingCompanyId) {
        this.shipingCompanyId = shipingCompanyId;
    }

    public void setShipingCompanyName(String shipingCompanyName) {
        this.shipingCompanyName = shipingCompanyName;
    }

    public void setShipingCost(BigDecimal shipingCost) {
        this.shipingCost = shipingCost;
    }

    public void setShipingCompanyDescription(String shipingCompanyDescription) {
        this.shipingCompanyDescription = shipingCompanyDescription;
    }

}
