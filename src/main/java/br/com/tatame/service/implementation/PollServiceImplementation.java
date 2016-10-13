package br.com.tatame.service.implementation;

import com.publisher.service.implementation.AbstractServiceImplementation;

import br.com.tatame.entity.Poll;
import br.com.tatame.service.PollService;

public class PollServiceImplementation extends AbstractServiceImplementation<Poll> implements PollService {

	@Override
	public Class<Poll> getServiceClass() {
		return Poll.class;
	}
}