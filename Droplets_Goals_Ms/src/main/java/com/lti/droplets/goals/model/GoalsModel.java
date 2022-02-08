package com.lti.droplets.goals.model;

import java.sql.Date;
import javax.persistence.*;

import lombok.Data;

@Data
@Entity
public class GoalsModel {
	@SequenceGenerator(
			name = "goals_sequence",
			sequenceName = "goals_sequence",
			allocationSize = 1
	)
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "goals_sequence"
	)

	private Long goalId;
	private Long customerId;
	private Long dropletId;
	private Date dropletStartDate; // locale
	private String eventType; // Enum
	private Double totalSavingsPercentage;
	private Double totalSavingsAmount;
	private Double minimumSavingsAmount;
	private Double maximumSavingsAmount;
	private int numberOfPayments;
	private int numberOfWeeks;
	private Date dropletEndDate;
	private Boolean isActivated;

}

