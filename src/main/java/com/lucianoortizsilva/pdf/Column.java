package com.lucianoortizsilva.pdf;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Column {

	private Integer indice;
	private String value = StringUtils.EMPTY;
	private Format format = Format.TEXT;

}