package com.dodonov.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class PageDto<T> {
	@Schema(description = "Номер страницы")
	private Integer page;
	@Schema(description = "Количество элементов на странице")
	private Integer size;
	@Schema(description = "Количество доступных страниц (при возврате ответа)")
	private Integer totalPages;
	@Schema(description = "Список сущностей (при возврате ответа)")
	private List<T> data;
}
