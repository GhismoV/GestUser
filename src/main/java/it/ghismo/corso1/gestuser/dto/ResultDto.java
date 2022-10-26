package it.ghismo.corso1.gestuser.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ResultDto {
	@NonNull private String code;
	private String message;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime date = LocalDateTime.now();

	public ResultDto(@NonNull String codice, String messaggio) { this(codice, messaggio, LocalDateTime.now()); }
	
}
