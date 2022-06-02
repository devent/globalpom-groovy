/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package name.fraser.neil.plaintext

import org.junit.jupiter.api.Test

/**
 *
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
class Diff_match_patchTest {

    @Test
    void "diff_commonPrefix"() {
        int expected = new diff_match_patch().diff_commonPrefix("aaa", "bbb")
        assert expected == 0
    }
}
