/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import org.ofbiz.base.util.*;
import org.ofbiz.product.catalog.*;
import org.ofbiz.product.category.*;
import javolution.util.FastMap;
import javolution.util.FastList;
import com.ilscipio.solr.SolrUtil;
import org.apache.solr.client.solrj.*;
import org.apache.solr.client.solrj.response.*;
import org.apache.commons.lang.StringUtils;

currentCatalogId = CatalogWorker.getCurrentCatalogId(request);
curCategoryId = parameters.category_id ?: parameters.CATEGORY_ID ?: parameters.productCategoryId ?: "";
curProductId = parameters.product_id ?: "" ?: parameters.PRODUCT_ID ?: "";    
topCategoryId = CatalogWorker.getCatalogTopCategoryId(request, currentCatalogId);

topLevelList = FastList.newInstance();
catLevel = [:];
res = dispatcher.runSync("solrSideDeepCategory",[productCategoryId:curCategoryId,catalogId:currentCatalogId]);
catLevel = res.get("categories");


//Debug.logInfo("catList "+catLevel,"");
context.currentCategoryPath = com.ilscipio.solr.CategoryUtil.getCategoryNameWithTrail(curCategoryId,false,dispatcher.getDispatchContext());
context.catList = catLevel;
context.topLevelList = [topCategoryId];
context.curCategoryId = curCategoryId;
context.topCategoryId = topCategoryId;