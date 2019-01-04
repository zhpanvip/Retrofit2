package com.zhpan.idea.net.token;

/**
 * Created by david on 16/11/24.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */

public interface IGlobalManager {
    /**
     * Exit the login state.
     */
    void logout();

    void tokenRefresh(RefreshTokenResponse response);
}
