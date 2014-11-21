/***************************************************************************
 * Copyright (C) 2003-2009 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 *
 **************************************************************************/
package org.exoplatform.clouddrive.cmis.ecms.viewer;

import org.exoplatform.container.xml.PortalContainerInfo;
import org.exoplatform.services.cms.impl.Utils;
import org.exoplatform.services.wcm.core.NodeLocation;
import org.exoplatform.services.wcm.utils.WCMCoreUtils;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.core.UIComponent;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;

/**
 *
 */
@ComponentConfig(template = "classpath:groovy/templates/VideoAudioViewer.gtmpl")
public class VideoAudioViewer extends UIComponent {

  private List<NodeLocation> presentNodes = new ArrayList<NodeLocation>();

  private String             repository;

  public VideoAudioViewer() throws Exception {
  }

  public List<Node> getPresentNodes() {
    List<Node> result = new ArrayList<Node>();
    result.addAll(NodeLocation.getNodeListByLocationList(presentNodes));
    return result;
  }

  public void setPresentNodes(List<Node> presentNodes) {
    this.presentNodes = NodeLocation.getLocationsByNodeList(presentNodes);
  }

  public void setRepository(String repository) {
    this.repository = repository;
  }

  public String getRepository() {
    return repository;
  }

  public String getPortalName() {
    PortalContainerInfo containerInfo = WCMCoreUtils.getService(PortalContainerInfo.class);
    return containerInfo.getContainerName();
  }

  public Node getFileLangNode(Node currentNode) throws Exception {
    if (currentNode.isNodeType("nt:unstructured")) {
      if (currentNode.getNodes().getSize() > 0) {
        NodeIterator nodeIter = currentNode.getNodes();
        while (nodeIter.hasNext()) {
          Node ntFile = nodeIter.nextNode();
          if (ntFile.isNodeType("nt:file")) {
            return ntFile;
          }
        }
        return currentNode;
      }
    }
    return currentNode;
  }

  public Node getChildNode(Node parent, String childType) throws Exception {
    return Utils.getChildOfType(parent, childType);
  }

}
