package com.ffinance.model

enum JobPosition {

    OTHER("OTHER"),
    IT("IT"),
    FINANCE_SECTOR("FINANCE SECTOR")

    final String name

    JobPosition(String name) {
        this.name = name
    }

}