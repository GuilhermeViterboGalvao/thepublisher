package br.com.clubetatame.manager;

import java.util.Map;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.publisher.entity.Account;

public class AuthenticationInterceptor implements Interceptor {

	private static final long serialVersionUID = -5134970557052915131L;

	@Override
	public void init() { }

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
        Action action = (Action)actionInvocation.getAction();
        if (action instanceof AccountAware) {
            Map<String, Object> session = actionInvocation.getInvocationContext().getSession();
            Account account = (Account)session.get("account");
            ((AccountAware)action).setAccount(account);
            if (account == null) {
            	return Action.LOGIN;
            }
        }
        return actionInvocation.invoke();
	}
	
	@Override
	public void destroy() { }
}