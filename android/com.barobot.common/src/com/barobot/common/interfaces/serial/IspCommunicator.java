package com.barobot.common.interfaces.serial;

public interface IspCommunicator {
    public boolean open();
    public boolean close();
    public int read(byte[] buf, int size);
    public int write(byte[] buf, int size);
    public int write(String s );
    public boolean isConnected();
    public void clearBuffer();
    public void reset(boolean b);
	public void destroy();
	public void init();
}
