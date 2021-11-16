package ca.sheridancollege.personInfo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfo implements Serializable {

	private static final long serialVersionUID = 8736599667617609648L;
	private int id;
	private String name;

}
