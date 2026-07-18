package com.alex.researchhub.paper.client;

import com.alex.researchhub.paper.dto.CrossrefResponse;

public interface CrossrefClient {

    CrossrefResponse fetchByDoi(String doi);

}