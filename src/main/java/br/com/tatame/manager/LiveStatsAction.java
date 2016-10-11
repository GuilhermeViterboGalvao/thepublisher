package br.com.tatame.manager;

import java.util.Collection;

import com.publisher.manager.AbstractAction;

import br.com.tatame.entity.LiveStats;
import br.com.tatame.service.LiveStatsService;

public abstract class LiveStatsAction extends AbstractAction<LiveStats> {

	private static final long serialVersionUID = -3365748162128998650L;

	private LiveStatsService liveStatsService;
	
	@Override
	protected void indexAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void populateForm(LiveStats entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected LiveStats updateObject(LiveStats entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected LiveStats createEmptyInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void saveObject(LiveStats entity, boolean isNew) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Collection<LiveStats> generateList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Collection<LiveStats> generateSearch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected LiveStats getObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
