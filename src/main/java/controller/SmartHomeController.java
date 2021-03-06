package controller;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import user.model.User;
import user.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Jingzhou Ou
 * Created on 17-12-3
 */
@Controller
@RequestMapping("/")
public class SmartHomeController {

    @Resource
    private UserService userService;

    /**
     * Update led status
     *
     * @param id
     * @param name
     * @param password
     * @param email
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "led/v1", method = RequestMethod.GET)
    public JSONObject updateLed(@RequestParam Integer id,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "password", required = false) String password,
                                @RequestParam(value = "email", required = false) String email) {
        User user = new User();
        user.setUserId(id);
        user.setUserName(name);
        user.setUserPassword(password);
        user.setUserEmail(email);
        JSONObject jsonObject = new JSONObject();
        try {
            if (userService.getUserById(id) != null) {
                int result = userService.updateUser(user);
                if (result > 0) {
                    jsonObject.put("Device", user.getUserId());
                    jsonObject.put("Status", user.getUserName());
                }
            } else {
                jsonObject.put("code", 400);
                jsonObject.put("desc", "led info does not exist");
            }
        } catch (Exception ex) {
            jsonObject.put("code", 500);
        }
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "device/id/{userId}", method = RequestMethod.GET)
    public JSONObject getUserById(@PathVariable Integer userId) {
        JSONObject jsonObject = new JSONObject();
        try {
            User user = userService.getUserById(userId);
            if (user != null) {
                jsonObject.put("id", user.getUserId());
                jsonObject.put("name", "temperature");
                jsonObject.put("status", user.getUserName());
            } else {
                jsonObject.put("code", 400);
                jsonObject.put("desc", "device does NOT exist");
            }
        } catch (Exception ex) {
            jsonObject.put("code", 500);
        }
        return jsonObject;
    }



    @ResponseBody
    @RequestMapping(value = "home/id/{userId}", method = RequestMethod.POST)
    public JSONObject upload(@PathVariable Integer userId, HttpServletRequest request) {
        Integer id = userId;
        String password = request.getParameter("Key");
        String name = request.getParameter("Light");
        String email = request.getParameter("Stove");


        User user = new User();
        user.setUserId(id);
        user.setUserName(name);
        user.setUserPassword(password);
        user.setUserEmail(email);
        JSONObject jsonObject = new JSONObject();
        try {
            if (userService.getUserById(id) != null) {
                int result = userService.updateUser(user);
                if (result > 0) {
                    jsonObject.put("Device", user.getUserId());
                    jsonObject.put("Status", user.getUserName());
                }
            } else {
                jsonObject.put("code", 400);
                jsonObject.put("desc", "led info does not exist");
            }
        } catch (Exception ex) {
            jsonObject.put("code", 500);
        }
        return jsonObject;
    }
}


