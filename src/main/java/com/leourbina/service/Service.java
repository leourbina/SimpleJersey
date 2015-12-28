package com.leourbina.service;

public interface Service {
  Service configure(ServiceConfiguration configuration);
  void run();
  void run(boolean join);
  void stop();
}
