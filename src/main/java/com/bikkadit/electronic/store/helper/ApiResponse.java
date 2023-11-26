package com.bikkadit.electronic.store.helper;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {

	private String message;
	private boolean success;
	
}
