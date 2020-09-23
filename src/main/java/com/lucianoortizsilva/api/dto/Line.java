package com.lucianoortizsilva.api.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Line {

	private List<Column> columns = new ArrayList<>();

	public Line(final Column... columns) {
		for (final Column column : columns) {
			this.columns.add(column);
		}
	}

}