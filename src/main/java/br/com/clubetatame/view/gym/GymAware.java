package br.com.clubetatame.view.gym;

import br.com.clubetatame.entity.Gym;

public interface GymAware {

	void setGym(Gym gym);
	
	Gym getGym();
}