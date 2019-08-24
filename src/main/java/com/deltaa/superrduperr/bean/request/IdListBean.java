package com.deltaa.superrduperr.bean.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class IdListBean {
	
	@NotNull(message = "Ids cannot be Blank")
	private List<@Valid IdBean> ids;

	public List<IdBean> getIds() {
		return ids;
	}

	public void setIds(List<IdBean> ids) {
		this.ids = ids;
	}
}
