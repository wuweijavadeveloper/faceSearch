package com.wuwei.hotel.test;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 * 所属包:test<br>
 * 类名:PlayMain<br>
 * -------------------<br>
 * 描述:加载本地库，仅限于windows系统<br>
 * -------------------<br>
 * 日期:2018年11月21日<br>
 * 作者:cuixin
 */
public class TestLib {
    private static final String NATIVE_LIBRARY_SEARCH_PATH = "F://VLC";

    public static void main(String[] args) {
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), NATIVE_LIBRARY_SEARCH_PATH);
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        boolean discover = new NativeDiscovery().discover();
        System.out.println(discover);
    }
}
