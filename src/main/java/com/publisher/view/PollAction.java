package com.publisher.view;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.publisher.entity.Alternative;
import com.publisher.entity.Poll;
import com.publisher.service.PollService;
import com.publisher.utils.IPUtils;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class PollAction extends ActionSupport implements ServletRequestAware {

	private static final long serialVersionUID = 8387590087242722744L;

	private HttpServletRequest request;
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	private PollService pollService;
	
	public void setPollService(PollService pollService) {
		this.pollService = pollService;
	}
	
	private Result result;
	
	public Result getResult() {
		return result;
	}
	
	@Override
	public String execute() throws Exception {
		if (pollId > 0 && !userHasAlreadyVoted()) {
			Poll poll = pollService.get(pollId);
			if (poll != null) {
				for (Alternative alternative : poll.getAlternatives()) {
					if (alternative.getId() == alternativeId) {
						alternative.setVotes(alternative.getVotes() + 1);
					}
				}
			}
			String key = getKey();
			CacheManager.getInstance().getCache("userPollVote").put(new Element(key, getClientIP(request)));
			pollService.update(poll);
			result = new Result(true);
		} else {
			result = new Result(false);
		}
		return SUCCESS;
	}
	
	public boolean userHasAlreadyVoted(){
		String key = getKey();
		return CacheManager.getInstance().getCache("userPollVote").get(key) != null;  
	}
	
	public String getKey() {
		return "[" + getClientIP(request) + "]-[" + request.getHeader("user-agent") + "]-[" + pollId + "]";
	}
	
	public String getClientIP(HttpServletRequest request) {
		return IPUtils.getClientIP(request);
	}
	
	//POJO
	
	private long pollId = 0;
	
	public void setPollId(long pollId) {
		this.pollId = pollId;
	}
	
	private long alternativeId = 0;
	
	public void setAlternativeId(long alternativeId) {
		this.alternativeId = alternativeId;
	}
	
	public class Result {
		
		public Result(boolean voted) {
			this.voted = voted;
		}
		
		private boolean voted = false;
		
		public boolean getVoted() {
			return voted;
		}
	}
}