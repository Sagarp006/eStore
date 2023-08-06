package com.shruteekatech.electronicstore.helper;

public interface AppConstants {
    //User Not Found Message
    String USER_NA = "User Not Found With Id = ";
    String USER_NA_EMAIL = "User Not Found With Email = ";

    //Message If name is in invalid format
    String NAME_VLD = "Name Must Be Min 5 and Max 20 Character long";

    //Message if password is in valid format
    String PASS_VLD = "Password Must Be Min 5 and Max 20 Character long";

    //Message if email is in valid format
    String EMAIL_VLD = "Email is Not in Valid Format";

    //Message If User is Deleted
    String USER_DEL = "User Deleted Successfully ";
    String GENDER_MSG = "Specify the Gender: Male or Female";
    String PASS_PATTERN = "^[A-Za-z0-9 .#&@-]+$";
    String PASS_PATTERN_MSG = "Password Must Contain Characters A-Z , a-z , 0-9 and special characters '.' '#' '&' '@' '-' ";
    String ABOUT_MSG = "Write Something about yourself";
    String NOT_AN_IMAGE = "Selected file is not an Image";
    String IMG_UPLOADED = "Image Uploaded Successfully!!!!";
    String CATEGORY_NA = "Category Not Found With Id =";
    String CATE_TITLE_BLANK="Title should not be left Blank";
    String CATE_TITLE_MIN="Category Title Must be Minimum of 4 Characters";
    String CATE_DES_BLANK="Description should not be left Blank";
    String CATE_IMG_BLANK="cover Image should not be left Blank";
    String CATE_DEL="Category Deleted Successfully";
    String PRODUCT_NA ="Product Not Found With Id: " ;
    String PRODUCT_DEL="Product deleted Successfully with id: ";
    Integer PAGE_NO=0;
}
