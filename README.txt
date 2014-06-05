TimeML-Normalizer
=================

Java application that, given a set of folders containing different TimeML annotations for the same docs, it normalizes the ids of the entities. For example, if one event is e1 in one annotation and the same event is referred as e251 in another annotations, they will obtain the same id after the normalization. The normalization takes into account all the TimeML elements.

LICENSE
  Copyright 2014 Hector Llorens

   Licensed under the Apache License, Version 2.0;
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.


Usage:
	java -jar path_to_tool_jar/timeml-normalizer.jar -a "annotation1;...;annotationN"
	NOTE: add -d option to see extra debug information

Examples:
java -jar target/timeml-normalizer.jar -a "sample-data/test-fold1;sample-data/TIPSem-fold1;sample-data/TIPSemB-fold1;sample-data/trios-fold1"



