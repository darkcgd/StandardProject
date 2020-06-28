/***
     * 获取用户信息
     * @param request
     * @return
     */
    public UserInfo getUserInfo(HttpServletRequest request) {
        UserInfo userInfo = new UserInfo();
        if (checkParams(request)) {
            try {
                String xUserId = request.getHeader("x-user-id");
                if (checkParams(xUserId)) {
                    xUserId = URLDecoder.decode(xUserId, "UTF-8");
                }
                String xUserName = request.getHeader("x-user-name");
                if (checkParams(xUserName)) {
                    xUserName = URLDecoder.decode(xUserName, "UTF-8");
                }

//                String xUserId = request.getHeader("userId");
//                if(checkParams(xUserId)){
//                    xUserId = URLDecoder.decode(xUserId, "UTF-8");
//                }
//                String xUserName = request.getHeader("servicerCode");
//                if(checkParams(xUserName)){
//                    xUserName = URLDecoder.decode(xUserName, "UTF-8");
//                }
                userInfo.setUserid(xUserId);
                userInfo.setUserName(xUserName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        if (!checkParams(userInfo)) {
            userInfo = new UserInfo();
        }
        return userInfo;
    }
