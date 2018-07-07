# SmartXMLAnalyzer
A simple web crawler that locates a user-selected element on a web site, gets attributes of the element and finds the most similar element on the sample page. Class, href, onClick and text are used for comparison.

Arguments:
- input_origin_file_path
- input_other_sample_file_path
- target_element_id

Output for sample pages:
sample-1-evil-gemini.html   -   #page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-body > a.btn.btn-success
sample-2-container-and-clone.html   -   #page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-body > div.some-container > a.btn.test-link-ok
sample-3-the-escape.html   -   #page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-footer > a.btn.btn-success
sample-4-the-mash.html   -   #page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-footer > a.btn.btn-success
