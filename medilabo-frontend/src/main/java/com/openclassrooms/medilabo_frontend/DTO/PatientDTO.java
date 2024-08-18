package com.openclassrooms.medilabo_frontend.DTO;

import java.sql.Date;

public record PatientDTO(int id, String lastName, String firstName, Date birthday, String gender, String address,
		String phone, String result) {

}
