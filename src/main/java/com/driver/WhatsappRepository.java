package com.driver;

import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Repository
public class WhatsappRepository
{
    private HashMap<String, User> usersDB = new HashMap<>(); // mobile No, User
    private HashMap<Group,List<User>> groupAndUsersList = new HashMap<>(); // Group, list of users of group

    private HashMap<Group,User> groupAndAdmin = new HashMap<>(); // group, admin(User Object)

    private HashMap<Group, List<Message>> groupAndMessagesDB = new HashMap<>(); // group, list of msgs


    private int groupCountTillNow = 0;

    private int msgCountTillNow = 0;



    public HashMap<String, User> getUsersDB() {
        return usersDB;
    }

    public int getGroupCountTillNow() {
        return groupCountTillNow;
    }

    public String saveUser(String name, String mobile) throws Exception {
        if(usersDB.containsKey(mobile))
            throw new Exception("User already exists");
        else
        {
            usersDB.put(mobile, new User(name, mobile));
            return "SUCCESS";
        }
    }

    public Group createGroup(List<User> users)
    {
        // if the list size is greater than 2
        if(users.size() > 2)
        {
            groupCountTillNow++;
            String groupName = "Group " + groupCountTillNow;
            int noOfParticipants = users.size();
            Group group = new Group(groupName, noOfParticipants);
            groupAndUsersList.put(group,users);
            return group;
        }
        else
        {
            String groupName = users.get(1).getName(); // second member name will be group name
            int participants = 2;
            Group group = new Group(groupName, participants);
            groupAndUsersList.put(group,users);
            groupAndAdmin.put(group,users.get(0));
            return group;
        }
    }

    public int createMessage(String content)
    {
        msgCountTillNow++;
        Message msg = new Message(msgCountTillNow, content);
        return msgCountTillNow;
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception
    {
        if(!groupAndMessagesDB.containsKey(group))
        {
            groupAndMessagesDB.put(group, new ArrayList<>());
        }
        // check if group is exist or not
        if(!groupAndUsersList.containsKey(group))
        {
            throw new Exception("Group does not exist");
        }
        // check sender is member of group or not
        List<User> users = groupAndUsersList.get(group);
        boolean isSenderMemberOfGroup = true;
        for(User user : users)
        {
            if(user == sender)
            {
                isSenderMemberOfGroup = true;
                break;
            }
        }

        if(isSenderMemberOfGroup == false) // if sender is not member of group
        {
            throw new Exception("You are not allowed to send message");
        }

        // need to return total msgs in the group
        List<Message> msgList = groupAndMessagesDB.get(group);
        msgList.add(message);
        groupAndMessagesDB.put(group, msgList);
        int countOfMsgsTillNowInCurrentGroup = msgList.size();
        return countOfMsgsTillNowInCurrentGroup;
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception {
        // check if group is exist or not
        if(!groupAndUsersList.containsKey(group))
        {
            throw new Exception("Group does not exist");
        }

        // if group exist check admin of the group
        User admin = groupAndAdmin.get(group);
        if(admin != approver)
        {
            throw new Exception("Approver does not have rights");
        }

        List<User> users = groupAndUsersList.get(group);
        boolean isUserParticipantOfGroup = false;
        for(User participant : users)
        {
            if(participant == user)
                isUserParticipantOfGroup = true;
        }
        // check user is participant or not
        if(isUserParticipantOfGroup == false)
            throw new Exception("User is not a participant");

        groupAndAdmin.put(group,user);
        return "SUCCESS";
    }
}
