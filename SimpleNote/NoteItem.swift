//
//  NoteItem.swift
//  SimpleNote
//
//  Created by 吉训振 on 2017/7/4.
//  Copyright © 2017年 吉训振. All rights reserved.
//

import Foundation
import UIKit

class NoteItem: UITableViewCell {
    
    @IBOutlet weak var noteContent: UILabel!
    @IBOutlet weak var noteTitle: UILabel!
    
    func setContent(note: Note) -> Void {
        let lines = note.context?.characters.split(separator: "\n").map(String.init)
        noteTitle.text = lines?[0]
        if (lines?.count)! > 1{
            noteContent.text = lines?[1]
        }else{
            noteContent.text = nil
        }
    }
}
