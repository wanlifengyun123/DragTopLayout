package com.example.yajun.dragtoplayout.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

/**
 * Fragment生成工具类
 *
 */
@SuppressWarnings({"unchecked", "hiding"})
public class FragmentFactory {

	/**
	 * 生成fragment(防止activity被恢复后.fragment加载两次) <li>fragment 必须要有空的构造方法 <li>
	 * fragment 的tag默认设置为类的simpleName
	 *
	 * @param fm
	 * @param clz
	 * @return
	 */
	public static <T extends Fragment> T createInstance(FragmentManager fm, Class<T> clz) {
		String tag = clz.getSimpleName();
		Fragment f = fm.findFragmentByTag(tag);
		if (f != null) {
			return (T) f;
		} else {
			try {
				return clz.newInstance();
			} catch (InstantiationException e) {
				throw new RuntimeException(tag + " 该类不能实例化.");
			} catch (IllegalAccessException e) {
				throw new RuntimeException(tag + " 没有参数为空的构造方法");
			}
		}
	}

	public static void addFragment(FragmentManager fm, int containerId, Fragment target) {
		if (!target.isAdded()) {
			fm.beginTransaction().add(containerId, target, target.getClass().getSimpleName()).commit();
		}
	}

	/**
	 * replace Fragment <li>该方法切换会销毁fragment的实例 <li>
	 * 被替换掉的fragment生命周期从onDestoryView-->onDestory-->onDetach <li>
	 * 要显示的fragment生命周期从onAttach-->onCreate-->onActivityCreated-->onCreateView
	 * <li>影响fragment属性isAdded()-->true isResumed()-->true isVisable() -->true
	 * <li>可通过isAdded来判断当前显示的Fragment
	 *
	 * @param fm          FragmentManager
	 * @param containerId 布局容器
	 * @param target      要替换的fragmetn实例
	 * @param tag         用于fm.findWithByTag(tag)找回该实例
	 * @param isAddtoBack 是否加入回退栈
	 */
	public static void replaceFragment(FragmentManager fm, int containerId, Fragment target, String tag,
									   boolean isAddtoBack) {
		FragmentTransaction ft = fm.beginTransaction();
		if (TextUtils.isEmpty(tag)) {
			tag = target.getClass().getSimpleName();
		}
		ft.replace(containerId, target, tag);
		ft.show(target);
		target.onHiddenChanged(false);
		if (isAddtoBack) {
			ft.addToBackStack(null);
		}
		ft.commit();
	}

	/**
	 * @link replaceFragment
	 */
	public static void replaceFragment(FragmentManager fm, int containerId, Fragment target, String tag) {
		replaceFragment(fm, containerId, target, tag, false);
	}

	/**
	 * @link replaceFragment
	 */
	public static void replaceFragment(FragmentManager fm, int containerId, Fragment target) {
		replaceFragment(fm, containerId, target, "", false);
	}

	/**
	 * 切换fragment <li>不会销毁fragment的实例 <li>不会影响fragment的生命周期 <li>
	 * 影响showFragment属性isAdded()-->true isResumed()-->true isVisable() -->true
	 * <li>影响hiddenFragment属性isVisable() -->false <li>相应调用fragment的
	 *
	 * @param fm
	 * @param containerId    布局容器
	 * @param showFragment   显示的fragment实例
	 * @param hiddenFragment 隐藏的fragment实例
	 * @param tag            为showFragment指定TAG
	 * @param isAddtoBack    是否加入回退栈
	 */
	public static void switchFragment(FragmentManager fm, int containerId, Fragment showFragment,
									  Fragment hiddenFragment, String tag, boolean isAddtoBack) {
		FragmentTransaction ft = fm.beginTransaction();
		ft.hide(hiddenFragment);
		hiddenFragment.onHiddenChanged(true);
		// ft.detach(hiddenFragment);
		if (showFragment.isAdded()) {// 先判断要显示的fragment是否添加过
			ft.show(showFragment);
			// ft.attach(showFragment);
		} else {
			ft.add(containerId, showFragment, tag);
		}
		showFragment.onHiddenChanged(false);
		if (isAddtoBack) {
			ft.addToBackStack(null);
		}
		ft.commit();
	}

	/**
	 * @param fm
	 * @param containerId
	 * @param showFragment
	 * @param hiddenFragment
	 * @param tag
	 */
	public static void switchFragment(FragmentManager fm, int containerId, Fragment showFragment,
									  Fragment hiddenFragment, String tag) {
		switchFragment(fm, containerId, showFragment, hiddenFragment, tag, false);
	}

}
