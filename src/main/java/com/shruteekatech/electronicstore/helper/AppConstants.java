package com.shruteekatech.electronicstore.helper;

public interface AppConstants {
    //User Not Found Message
    String UNF_ID ="User Not Found With Id = ";
    String UNF_EMAIL="User Not Found With Email = ";

    //Message If name is in invalid format
    String NAME_VLD ="Name Must Be Min 5 and Max 20 Character long";

   //Message if password is i in valid format
    String PASS_VLD="Password Must Be Min 5 and Max 20 Character long";

    //Message if email is i in valid format
    String EMAIL_VLD="Email is Not in Valid Format";

    //Message If User is Deleted
    String USER_DEL="User Deleted Successfully ";
    String GENDER_MSG="Pls Specify the Gender: Male or Female";
    String PASS_PATTERN = "^[A-Za-z0-9 .#&@-]+$";
}
