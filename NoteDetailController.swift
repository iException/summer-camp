//
//  NoteDetailController.swift
//  SimpleNote
//
//  Created by 吉训振 on 2017/7/4.
//  Copyright © 2017年 吉训振. All rights reserved.
//

import Foundation
import UIKit

class NoteDetailController: UIViewController {
    @IBOutlet weak var context: UITextView!
    @IBOutlet weak var share: UIButton!
    @IBOutlet weak var delete: UIButton!
    
    var status: OpenStatus?
    var indexPath: IndexPath?
    
    var delegate: NoteDetailDelegate?
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    @IBAction func back(_ sender: UIButton) {
        
        if context.text != nil {
            if status == OpenStatus.NEW {
                delegate?.newNote(content: context.text)
            }
            else {
                delegate?.modifyNote(content: context.text, indexPath: indexPath!)
            }
        }
        self.dismiss(animated: true, completion: nil)
    }
    
    
    @IBAction func deleteNote(_ sender: UIButton) {
        if status == OpenStatus.MODIFY {
            delegate?.deleteNote(indexPath: indexPath!)
        }
    }
    
    @IBAction func shareNote(_ sender: UIButton) {
    }

}

protocol NoteDetailDelegate {
    func newNote(content: String)
    func modifyNote(content: String, indexPath: IndexPath)
    func deleteNote(indexPath: IndexPath)
}

enum OpenStatus{
    case NEW
    case MODIFY
}
