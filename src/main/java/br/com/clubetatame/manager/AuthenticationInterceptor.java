package br.com.clubetatame.manager;

import java.util.Map;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.publisher.entity.Account;
import com.publisher.manager.AccountAware;

public class AuthenticationInterceptor implements Interceptor {

	private static final long serialVersionUID = -5134970557052915131L;

	@Override
	public void init() { }

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
        Action action = (Action)actionInvocation.getAction();
        if (action instanceof AccountAware) {
            Map<String, Object> session = actionInvocation.getInvocationContext().getSession();
            Object obj = session != null ? session.get("account") : null;
            if (obj != null && obj instanceof Account) {
                ((AccountAware)action).setAccount((Account)obj);             
            } else {
            	return Action.LOGIN;
            }
        }
        return actionInvocation.invoke();
	}
	
	@Override
	public void destroy() { }
}