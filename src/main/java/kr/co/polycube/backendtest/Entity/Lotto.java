package kr.co.polycube.backendtest.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "\"lotto\"")
public class Lotto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private int number_1;
	private int number_2;
	private int number_3;
	private int number_4;
	private int number_5;
	private int number_6;
	

}
