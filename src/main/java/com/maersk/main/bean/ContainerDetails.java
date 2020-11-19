package com.maersk.main.bean;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("bookings")
public class ContainerDetails {
	
	@Column("id")
	private Long id;
	
	@Column("container_type")
	private String containerType;
	
	@Column("size")
	private Long size;
	
	@Column("destination")
	private String destination;
	
	@Column("origin")
	private String origin;
	
	@Column("quantity")
	private Long quantity;
	
	@PrimaryKey
	@Column("booking_ref")
	private Long booking_ref;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContainerType() {
		return containerType;
	}
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}	
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public Long getBookingRef() {
		return booking_ref;
	}
	public void setBookingRef(Long booking_ref) {
		this.booking_ref = booking_ref;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContainerDetails [Id=");
		builder.append(id);
		builder.append(", containerType=");
		builder.append(containerType);
		builder.append(", containerSize=");
		builder.append(size);
		builder.append(", destination=");
		builder.append(destination);
		builder.append(", origin=");
		builder.append(origin);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append("]");
		return builder.toString();
	}
}