package com.hastatakip.job;

import javax.ejb.Remote;

@Remote
public interface TimerSampler {
	void scheduleTimer(long milliseconds);
}