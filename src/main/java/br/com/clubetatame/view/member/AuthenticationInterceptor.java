package br.com.clubetatame.view.member;

import java.util.Map;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import br.com.clubetatame.entity.Member;

public class AuthenticationInterceptor implements Interceptor {

	private static final long serialVersionUID = -4521496263675804357L;

	@Override
	public void init() { }

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
        Action action = (Action)actionInvocation.getAction();
        if (action instanceof MemberAware) {
            Map<String, Object> session = actionInvocation.getInvocationContext().getSession();
            Object obj = session != null ? session.get("member") : null;
            if (obj != null && obj instanceof Member) {
                ((MemberAware)action).setMember((Member)obj);             
            } else {
            	return Action.LOGIN;
            }
        }
        return actionInvocation.invoke();
	}
	
	@Override
	public void destroy() { }
}