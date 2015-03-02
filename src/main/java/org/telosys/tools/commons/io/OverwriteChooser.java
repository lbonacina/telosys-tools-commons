/**
 *  Copyright (C) 2008-2015  Telosys project org. ( http://www.telosys.org/ )
 *
 *  Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.gnu.org/licenses/lgpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.telosys.tools.commons.io;

public interface OverwriteChooser {

	public final static int YES        = 0 ;
//	public final static int YES_TO_ALL = 1 ;
	public final static int NO         = 2 ;
//	public final static int NO_TO_ALL  = 3 ;
	public final static int CANCEL     = 4 ;
	
	int choose(String fullFileName) ;

	int choose(String fileName, String folderName) ;
}
