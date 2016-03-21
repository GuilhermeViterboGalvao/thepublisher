package br.com.clubetatame.view.member;

import br.com.clubetatame.entity.Member;

public interface MemberAware {

	void setMember(Member member);
	
	Member getMember();
}
