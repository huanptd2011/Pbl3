package com.nahuannghia.shopnhn.model;

import java.time.LocalDateTime;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Table(name = "cancellation_history")
public class CancellationHistory {
  @Id
  @Column(name = "cancellation_id", length = 30, nullable = false)
  private String cancellationID;

  @ManyToOne
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customerID;

  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private Order orderID;

  @Column(name = "cancellation_date", nullable = false)
  private LocalDateTime cancellationDate;
  @Column(name = "cancellation_reason", length = 2000)
  private String cancellationReason;

  public CancellationHistory() {
  }

  public CancellationHistory(String cancellationID, Customer customerID, Order orderID, LocalDateTime cancellationDate,
      String cancellationReason) {
    this.cancellationID = cancellationID;
    this.customerID = customerID;
    this.orderID = orderID;
    this.cancellationDate = cancellationDate;
    this.cancellationReason = cancellationReason;
  }

  // Getters
  public String getCancellationID() {
    return cancellationID;
  }

  public Customer getCustomerID() {
    return customerID;
  }

  public Order getOrderID() {
    return orderID;
  }

  public LocalDateTime getCancellationDate() {
    return cancellationDate;
  }

  public String getCancellationReason() {
    return cancellationReason;
  }

  // Setters
  public void setCancellationID(String cancellationID) {
    this.cancellationID = cancellationID;
  }

  public void setCustomerID(Customer customerID) {
    this.customerID = customerID;
  }

  public void setOrderID(Order orderID) {
    this.orderID = orderID;
  }

  public void setCancellationDate(LocalDateTime cancellationDate) {
    this.cancellationDate = cancellationDate;
  }

  public void setCancellationReason(String cancellationReason) {
    this.cancellationReason = cancellationReason;
  }
}
