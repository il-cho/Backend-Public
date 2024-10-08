package com.ssafy.userservice.oAuth2.authrepository;

import com.ssafy.userservice.oAuth2.authUtil.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Component
public class CustomAuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private static final String DEFAULT_AUTHORIZATION_REQUEST_ATTR_NAME = HttpSessionOAuth2AuthorizationRequestRepository.class.getName() + ".AUTHORIZATION_REQUEST";
    private final String sessionAttributeName;
    private final String ONESECOND_MODE_PARAM = "mode";

    public CustomAuthorizationRequestRepository() {
        this.sessionAttributeName = DEFAULT_AUTHORIZATION_REQUEST_ATTR_NAME;
    }

    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Assert.notNull(request, "request cannot be null");
        String stateParameter = this.getStateParameter(request);
        if (stateParameter == null) {
            return null;
        } else {
            OAuth2AuthorizationRequest authorizationRequest = this.getAuthorizationRequest(request);
            return authorizationRequest != null && stateParameter.equals(authorizationRequest.getState()) ? authorizationRequest : null;
        }
    }

    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(request, "request cannot be null");
        Assert.notNull(response, "response cannot be null");

        if (authorizationRequest == null) {
            this.removeAuthorizationRequest(request, response);
            CookieUtil.deleteCookie(request, response, ONESECOND_MODE_PARAM);
        } else {
            String state = authorizationRequest.getState();
            Assert.hasText(state, "authorizationRequest.state cannot be empty");
            request.getSession().setAttribute(this.sessionAttributeName, authorizationRequest);
        }

        String mode = request.getParameter(ONESECOND_MODE_PARAM);
        Assert.hasText(mode, "request.mode cannot be empty");
        CookieUtil.addCookie(response, ONESECOND_MODE_PARAM, mode, 3000);
    }

    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(response, "response cannot be null");
        OAuth2AuthorizationRequest authorizationRequest = this.loadAuthorizationRequest(request);
        if (authorizationRequest != null) {
            request.getSession().removeAttribute(this.sessionAttributeName);
        }

        return authorizationRequest;
    }

    private String getStateParameter(HttpServletRequest request) {
        return request.getParameter("state");
    }

    private OAuth2AuthorizationRequest getAuthorizationRequest(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null ? (OAuth2AuthorizationRequest)session.getAttribute(this.sessionAttributeName) : null;
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, ONESECOND_MODE_PARAM);
    }
}
