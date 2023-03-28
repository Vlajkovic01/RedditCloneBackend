package com.example.RedditClone.service;

import com.example.RedditClone.model.enumeration.MessageType;

public interface LogService {
    void message(String message, MessageType type);
}
