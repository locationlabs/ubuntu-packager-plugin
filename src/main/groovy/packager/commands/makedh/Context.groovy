/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package packager.commands.makedh

import static org.apache.commons.lang.StringUtils.*

@Immutable
final class Context {

    String name
    String version
    String releaseNotes
    String author
    String email
    String homepage
    String time
    String description
    SortedSet<String> dirs
    SortedSet<String> depends

    def asType(Class type) {
        "to${capitalise(type.simpleName)}"()
    }

    Map toMap() {
        [name:name, version:version, releaseNotes:releaseNotes, author:author, email:email, time:time, dirs:dirs, homepage:homepage, description:description, depends:depends]
    }
}
