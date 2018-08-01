package com.ayantsoft.interview.jsf.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import com.ayantsoft.expense.service.CandidateService;
import com.ayantsoft.interview.hibernate.pojo.Candidate;

public class LazyCandidateDataModel extends LazyDataModel<Candidate> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3545572938943107075L;

	private CandidateService candidateService;

	public LazyCandidateDataModel(CandidateService candidateService) {
		super();
		this.candidateService = candidateService;
	}

	@Override
	public Object getRowKey(Candidate candidate) {
		//System.out.println("getRowKey Called");
		return candidate.getId();
	}

	@Override
	public Candidate getRowData(String rowKey) {
		List<Candidate> candidate = (List<Candidate>) getWrappedData();
		Integer value = Integer.valueOf(rowKey);

		for (Candidate candi : candidate){
			if (candi.getId().equals(value)){
				return candi;
			}
		}

		return null;
	}
	
	@Override
	public List<Candidate> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {


		Object[] resultWithCount = candidateService.expenseFilter(first, pageSize, sortField, sortOrder, filters);

		System.out.println("first: "+ first + " pageSize: "+  pageSize +" sortField: "+ sortField +" SortOrder: "+  sortOrder);

		filters.forEach((k,v)->{
			System.out.println("filterKey : " + k + " filterValue : " + v);
		});

		this.setRowCount(((Long)resultWithCount[0]).intValue());
		return (List<Candidate>) resultWithCount[1];

	}

	//getter and setter start hear

	public CandidateService getCandidateService() {
		return candidateService;
	}

	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}


}
