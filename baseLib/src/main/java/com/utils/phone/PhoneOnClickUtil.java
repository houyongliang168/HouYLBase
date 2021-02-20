package com.utils.phone;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.utils.log.MyToast;


/**
 * <pre>
 *     author : REN SHI QIAN
 *     time   : 2019/03/20
 *     desc   :
 *     remarks:
 * </pre>
 */

public class PhoneOnClickUtil {

    /**
     * 拨打电话
     * @param context
     * @param phone
     */
    public static void call(Context context, String phone){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);//直接拨出电话
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }
    /**
     * 调用系统界面，给指定的号码发送短信，并附带短信内容
     *
     * @param context
     * @param number
     * @param body
     */
    public static void sendSmsWithBody(Context context, String number, String body) {
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(Uri.parse("smsto:" + number));
        if (!TextUtils.isEmpty(body)) {
            sendIntent.putExtra("sms_body", body);
        }
        context.startActivity(sendIntent);
    }


    /*保存电话*/
    //根据电话号码查询姓名（在一个电话打过来时，如果此电话在通讯录中，则显示姓名）
    public static void savePhone(Context context, String name, String phone) {

        Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/" + phone);
        ContentResolver resolver = context.getContentResolver();
//        ContactsContract.Data.DISPLAY_NAME 查询 该电话的客户姓名

        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data.HAS_PHONE_NUMBER}, null, null, null); //从raw_contact表中返回display_name
        int count = cursor.getCount();

        if (count > 0) {
            if (cursor.moveToFirst()) {
                String hasPhone = cursor.getString(0);//查询该电话有没有人
                if (TextUtils.isEmpty(hasPhone)) {//没有该电话
                    insertPhone(context, name, phone);
                } else if ("0".equals(hasPhone)) {//没有该电话
                    insertPhone(context, name, phone);
                } else {
                    MyToast.showShort("该电话号码已存在!");
                }

            }
        } else {
            insertPhone(context, name, phone);
        }

        cursor.close();
    }

    /*插入姓名和 电话*/

    private static void insertPhone(Context context, String name, String phone) {
        if (TextUtils.isEmpty(name)) {
            name = phone;
        }

//        //插入raw_contacts表，并获取_id属性
//        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
//        ContentResolver resolver = context.getContentResolver();
//        ContentValues values = new ContentValues();
//        long contact_id = ContentUris.parseId(resolver.insert(uri, values));
//        //插入data表
//        uri = Uri.parse("content://com.android.contacts/data");
//        //add Name
//        values.put("raw_contact_id", contact_id);
//        values.put(ContactsContract.Data.MIMETYPE,"vnd.android.cursor.item/name");
//        values.put("data2", name);
//        values.put("data1", name);
//        resolver.insert(uri, values);
//        values.clear();
//        //add Phone
//        values.put("raw_contact_id", contact_id);
//        values.put(ContactsContract.Data.MIMETYPE,"vnd.android.cursor.item/phone_v2");
//        values.put("data2", "2");   //手机
//        values.put("data1", phone);
//        resolver.insert(uri, values);
//        values.clear();


        ContentValues values = new ContentValues(); //首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId      
        Uri rawContactUri = context.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);//获取id     
        long rawContactId = ContentUris.parseId(rawContactUri); //往data表入姓名数据         
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId); //添加id         
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);//添加内容类型（MIMETYPE）     
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);//添加名字，添加到first name位置        
        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values); //往data表入电话数据        
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone);
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
        MyToast.showShort("保存电话");
    }
}
