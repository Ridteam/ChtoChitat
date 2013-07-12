package com.ridteam.livejournal.chtochitat.xmlrpc;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;

import com.ridteam.livejournal.chtochitat.utils.Utils;
import com.ridteam.livejournal.chtochitat.xmlrpc.entities.BaseEntity;

public class XmlRpcSerializer
{

	private static final String TAG = "XmlRpcSerializer";

	public XmlRpcSerializer()
	{

	}

	public String writeParamsToXmlRpc(final BaseEntity entity, final String methodName) throws IllegalArgumentException, IllegalStateException, IOException
	{
		StringWriter writer = new StringWriter();
		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(writer);
		serializer.startDocument("UTF-8", true);
		serializer.startTag(null, XmlRpcTag.TAG_METHOD_CALL);
		serializer.startTag(null, XmlRpcTag.TAG_METHOD_NAME).text(methodName).endTag(null, XmlRpcTag.TAG_METHOD_NAME);
		serializer.startTag(null, XmlRpcTag.TAG_PARAMS);
		serializer.startTag(null, XmlRpcTag.TAG_PARAM);
		serializer.startTag(null, XmlRpcTag.TAG_VALUE);
		serializer.startTag(null, XmlRpcTag.TAG_STRUCT);

		for (Map.Entry<String, Object> entry : entity.getMap().entrySet())
		{
			serializer.startTag(null, XmlRpcTag.TAG_MEMBER);

			writeTagName(serializer, entry.getKey());
			writeTagValue(serializer, entry.getValue());

			serializer.endTag(null, XmlRpcTag.TAG_MEMBER);

		}
		serializer.endTag(null, XmlRpcTag.TAG_STRUCT);
		serializer.endTag(null, XmlRpcTag.TAG_VALUE);
		serializer.endTag(null, XmlRpcTag.TAG_PARAM);
		serializer.endTag(null, XmlRpcTag.TAG_PARAMS);
		serializer.endTag(null, XmlRpcTag.TAG_METHOD_CALL);
		serializer.endDocument();
		return writer.toString();
	}

	private void writeTagName(XmlSerializer serializer, String name) throws IllegalArgumentException, IllegalStateException, IOException
	{
		Utils.logDebug(TAG + " writeTagName", name);
		serializer.startTag(null, XmlRpcTag.TAG_NAME).text(name).endTag(null, XmlRpcTag.TAG_NAME);
	}

	private void writeTagValue(XmlSerializer serializer, Object value) throws IllegalArgumentException, IllegalStateException, IOException
	{
		Utils.logDebug(TAG + " writeTagValue", value + "");
		serializer.startTag(null, XmlRpcTag.TAG_VALUE);

		final String type = getTagFromObject(value);
		if (type.equals(XmlRpcTypeTag.TYPE_ARRAY))
		{
			writeTagArrayValue(serializer, value);
		}
		else
		{
			serializer.startTag(null, type).text(value.toString()).endTag(null, type);
		}

		serializer.endTag(null, XmlRpcTag.TAG_VALUE);
	}

	private void writeTagArrayValue(XmlSerializer serializer, Object value)
	{
		final List<Object> list = Arrays.asList(value);
		final String sClass = value.getClass().getSimpleName();
		if (sClass.equals("String[]") || sClass.equals("Integer[]") || sClass.equals("Double[]") || sClass.equals("Long[]") || sClass.equals("Boolean[]"))
		{
			writeArray(serializer, list);
		}
		else if (sClass.equals("LinkedList") || sClass.equals("ArrayList") || sClass.equals("List"))
		{

		}
		else if (sClass.equals("LinkedHashMap") || sClass.equals("HashMap") || sClass.equals("Map"))
		{

		}
		else
		{
			Log.e(TAG, "Unknown array type value");
		}
	}

	private void writeArray(XmlSerializer serializer, final List<Object> list)
	{
		for (int i = 0; i < list.size(); i++)
		{
			Object[] arrayList = (Object[]) list.get(i);
			Log.i("MyTag", arrayList.getClass().getSimpleName());
			for (Object object : arrayList)
			{
				Log.i("MyTag", object + "");
			}
		}
	}

	private String getTagFromObject(final Object object)
	{
		final String sClass = object.getClass().getSimpleName();
		if (sClass.equals("String"))
		{
			return XmlRpcTypeTag.TYPE_STRING;
		}
		else if (sClass.equals("Integer"))
		{
			return XmlRpcTypeTag.TYPE_I4;
		}
		else if (sClass.equals("Double"))
		{
			return XmlRpcTypeTag.TYPE_DOUBLE;
		}
		else if (sClass.equals("Boolean"))
		{
			return XmlRpcTypeTag.TYPE_BOOLEAN;
		}
		else if (sClass.equals("Date"))
		{
			return XmlRpcTypeTag.TYPE_DATE_TIME_ISO8601;
		}
		else if (sClass.equals("ArrayList"))
		{
			return XmlRpcTypeTag.TYPE_ARRAY;
		}
		else if (sClass.equals("LinkedList"))
		{
			return XmlRpcTypeTag.TYPE_ARRAY;
		}
		else if (sClass.equals("LinkedHashMap"))
		{
			return XmlRpcTypeTag.TYPE_ARRAY;
		}
		else if (sClass.equals("HashMap"))
		{
			return XmlRpcTypeTag.TYPE_ARRAY;
		}
		else
		{
			Log.e(TAG, "Unknown type value");
			return XmlRpcTypeTag.TYPE_ARRAY;
		}
	}

}
