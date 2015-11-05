package com.pyler.quickpic2gallery;

import android.content.res.XModuleResources;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;

public class QuickPic2Gallery implements IXposedHookZygoteInit,
		IXposedHookInitPackageResources {
	private static String MODULE_PATH = null;
	private static String QUICKPIC_PKG = "com.alensw.PicFolder";

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		MODULE_PATH = startupParam.modulePath;
	}

	@Override
	public void handleInitPackageResources(InitPackageResourcesParam resparam)
			throws Throwable {
		if (!QUICKPIC_PKG.equals(resparam.packageName)) {
			return;
		}
		XSharedPreferences prefs = new XSharedPreferences(
				QuickPic2Gallery.class.getPackage().getName());
		prefs.makeWorldReadable();
		prefs.reload();
		boolean isCustomName = prefs.getBoolean("custom_name", false);
		XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH,
				resparam.res);
		if (isCustomName) {
			String customQuickPicName = prefs.getString("quickpic_name",
					"Gallery");
			resparam.res.setReplacement("com.alensw.PicFolder", "string",
					"app_name", customQuickPicName);
			resparam.res.setReplacement("com.alensw.PicFolder", "string",
					"main_title_name", customQuickPicName);
		} else {
			resparam.res.setReplacement("com.alensw.PicFolder", "string",
					"app_name", modRes.fwd(R.string.gallery));
			resparam.res.setReplacement("com.alensw.PicFolder", "string",
					"main_title_name", modRes.fwd(R.string.gallery));
		}
	}
}