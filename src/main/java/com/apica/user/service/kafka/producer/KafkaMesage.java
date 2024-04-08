package com.apica.user.service.kafka.producer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMesage {
	private Integer userId;
	private String eventType;
	private String timeStamp;
}
